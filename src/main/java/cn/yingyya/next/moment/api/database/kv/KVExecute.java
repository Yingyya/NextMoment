package cn.yingyya.next.moment.api.database.kv;

import cn.yingyya.next.moment.api.database.DataConnector;
import org.jetbrains.annotations.NotNull;
import org.rocksdb.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KVExecute {

	private final RocksDB db;

	public KVExecute(@NotNull DataConnector<?> dataConnector) {
		if (dataConnector.dataSource() instanceof RocksDB) {
			this.db = (RocksDB) dataConnector.dataSource();
		}
		throw new IllegalStateException("DataConnector is not a LevelDB instance");
	}

	public static KVExecute of(@NotNull DataConnector<?> dataConnector) {
		return new KVExecute(dataConnector);
	}

	public boolean set(byte[] key, byte[] value) {
		try {
			db.put(key, value);
			return true;
		} catch (RocksDBException e) {
			e.printStackTrace();
			return false;
		}
	}

	public byte[] get(byte[] key, byte[] defaultValue) {
		try {
			byte[] bytes = db.get(key);
			return bytes != defaultValue ? bytes : null;
		} catch (RocksDBException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public boolean delete(byte[] key) {
		try {
			db.delete(key);
			return true;
		} catch (RocksDBException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteRange(byte[] start, byte[] end) {
		try {
			db.deleteRange(start, end);
			return true;
		} catch (RocksDBException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean flush() {
		try {
			db.flush(new FlushOptions());
			return true;
		} catch (RocksDBException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<byte[]> multiGetAsList(List<byte[]> keys) {
		try {
			List<byte[]> bytes = db.multiGetAsList(keys);
			if (bytes == null) {
				return Collections.emptyList();
			}
			return bytes;
		} catch (RocksDBException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public Map<byte[], byte[]> multiGetAsMap(List<byte[]> keys) {
		try {
			return db.multiGet(keys);
		} catch (RocksDBException e) {
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}

	public boolean keyMayExist(byte[] key) {
		try {
			Holder<byte[]> holder = new Holder<>();
			return db.keyMayExist(key, holder);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Map<byte[], byte[]> getAll() {
		Map<byte[], byte[]> map = new HashMap<>();
		try (final RocksIterator iterator = db.newIterator()) {
			for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
				map.put(iterator.key(), iterator.value());
			}
		}
		return map;
	}
}
