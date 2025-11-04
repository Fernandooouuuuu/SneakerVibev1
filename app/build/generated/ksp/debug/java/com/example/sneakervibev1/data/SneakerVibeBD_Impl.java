package com.example.sneakervibev1.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.sneakervibev1.data.dao.CarritoDao;
import com.example.sneakervibev1.data.dao.CarritoDao_Impl;
import com.example.sneakervibev1.data.dao.ProductoDao;
import com.example.sneakervibev1.data.dao.ProductoDao_Impl;
import com.example.sneakervibev1.data.dao.UsuarioDao;
import com.example.sneakervibev1.data.dao.UsuarioDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SneakerVibeBD_Impl extends SneakerVibeBD {
  private volatile UsuarioDao _usuarioDao;

  private volatile ProductoDao _productoDao;

  private volatile CarritoDao _carritoDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `usuario` (`id_usuario` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `correo` TEXT NOT NULL, `password` TEXT NOT NULL, `rol` TEXT NOT NULL, `activo` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categoria` (`id_categoria` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre_categoria` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `producto` (`id_producto` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id_categoria` INTEGER NOT NULL, `nombre_producto` TEXT NOT NULL, `descripcion` TEXT NOT NULL, `precio` REAL NOT NULL, `stock` INTEGER NOT NULL, `imagen` TEXT, `activo` INTEGER NOT NULL, FOREIGN KEY(`id_categoria`) REFERENCES `categoria`(`id_categoria`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `carrito` (`id_carrito` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id_usuario` INTEGER NOT NULL, `creado_en` TEXT NOT NULL, FOREIGN KEY(`id_usuario`) REFERENCES `usuario`(`id_usuario`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `detalle_carrito` (`id_detalle` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id_carrito` INTEGER NOT NULL, `id_producto` INTEGER NOT NULL, `cantidad` INTEGER NOT NULL, `subtotal` REAL NOT NULL, FOREIGN KEY(`id_carrito`) REFERENCES `carrito`(`id_carrito`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`id_producto`) REFERENCES `producto`(`id_producto`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '23ea3d0f3cdf350fc4210f0b017c877e')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `usuario`");
        db.execSQL("DROP TABLE IF EXISTS `categoria`");
        db.execSQL("DROP TABLE IF EXISTS `producto`");
        db.execSQL("DROP TABLE IF EXISTS `carrito`");
        db.execSQL("DROP TABLE IF EXISTS `detalle_carrito`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsuario = new HashMap<String, TableInfo.Column>(6);
        _columnsUsuario.put("id_usuario", new TableInfo.Column("id_usuario", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuario.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuario.put("correo", new TableInfo.Column("correo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuario.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuario.put("rol", new TableInfo.Column("rol", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuario.put("activo", new TableInfo.Column("activo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsuario = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsuario = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsuario = new TableInfo("usuario", _columnsUsuario, _foreignKeysUsuario, _indicesUsuario);
        final TableInfo _existingUsuario = TableInfo.read(db, "usuario");
        if (!_infoUsuario.equals(_existingUsuario)) {
          return new RoomOpenHelper.ValidationResult(false, "usuario(com.example.sneakervibev1.data.entidades.Usuario).\n"
                  + " Expected:\n" + _infoUsuario + "\n"
                  + " Found:\n" + _existingUsuario);
        }
        final HashMap<String, TableInfo.Column> _columnsCategoria = new HashMap<String, TableInfo.Column>(2);
        _columnsCategoria.put("id_categoria", new TableInfo.Column("id_categoria", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategoria.put("nombre_categoria", new TableInfo.Column("nombre_categoria", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategoria = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategoria = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategoria = new TableInfo("categoria", _columnsCategoria, _foreignKeysCategoria, _indicesCategoria);
        final TableInfo _existingCategoria = TableInfo.read(db, "categoria");
        if (!_infoCategoria.equals(_existingCategoria)) {
          return new RoomOpenHelper.ValidationResult(false, "categoria(com.example.sneakervibev1.data.entidades.Categoria).\n"
                  + " Expected:\n" + _infoCategoria + "\n"
                  + " Found:\n" + _existingCategoria);
        }
        final HashMap<String, TableInfo.Column> _columnsProducto = new HashMap<String, TableInfo.Column>(8);
        _columnsProducto.put("id_producto", new TableInfo.Column("id_producto", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("id_categoria", new TableInfo.Column("id_categoria", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("nombre_producto", new TableInfo.Column("nombre_producto", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("descripcion", new TableInfo.Column("descripcion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("precio", new TableInfo.Column("precio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("stock", new TableInfo.Column("stock", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("imagen", new TableInfo.Column("imagen", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("activo", new TableInfo.Column("activo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducto = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysProducto.add(new TableInfo.ForeignKey("categoria", "CASCADE", "NO ACTION", Arrays.asList("id_categoria"), Arrays.asList("id_categoria")));
        final HashSet<TableInfo.Index> _indicesProducto = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProducto = new TableInfo("producto", _columnsProducto, _foreignKeysProducto, _indicesProducto);
        final TableInfo _existingProducto = TableInfo.read(db, "producto");
        if (!_infoProducto.equals(_existingProducto)) {
          return new RoomOpenHelper.ValidationResult(false, "producto(com.example.sneakervibev1.data.entidades.Producto).\n"
                  + " Expected:\n" + _infoProducto + "\n"
                  + " Found:\n" + _existingProducto);
        }
        final HashMap<String, TableInfo.Column> _columnsCarrito = new HashMap<String, TableInfo.Column>(3);
        _columnsCarrito.put("id_carrito", new TableInfo.Column("id_carrito", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarrito.put("id_usuario", new TableInfo.Column("id_usuario", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarrito.put("creado_en", new TableInfo.Column("creado_en", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCarrito = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCarrito.add(new TableInfo.ForeignKey("usuario", "CASCADE", "NO ACTION", Arrays.asList("id_usuario"), Arrays.asList("id_usuario")));
        final HashSet<TableInfo.Index> _indicesCarrito = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCarrito = new TableInfo("carrito", _columnsCarrito, _foreignKeysCarrito, _indicesCarrito);
        final TableInfo _existingCarrito = TableInfo.read(db, "carrito");
        if (!_infoCarrito.equals(_existingCarrito)) {
          return new RoomOpenHelper.ValidationResult(false, "carrito(com.example.sneakervibev1.data.entidades.Carrito).\n"
                  + " Expected:\n" + _infoCarrito + "\n"
                  + " Found:\n" + _existingCarrito);
        }
        final HashMap<String, TableInfo.Column> _columnsDetalleCarrito = new HashMap<String, TableInfo.Column>(5);
        _columnsDetalleCarrito.put("id_detalle", new TableInfo.Column("id_detalle", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetalleCarrito.put("id_carrito", new TableInfo.Column("id_carrito", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetalleCarrito.put("id_producto", new TableInfo.Column("id_producto", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetalleCarrito.put("cantidad", new TableInfo.Column("cantidad", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDetalleCarrito.put("subtotal", new TableInfo.Column("subtotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDetalleCarrito = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysDetalleCarrito.add(new TableInfo.ForeignKey("carrito", "CASCADE", "NO ACTION", Arrays.asList("id_carrito"), Arrays.asList("id_carrito")));
        _foreignKeysDetalleCarrito.add(new TableInfo.ForeignKey("producto", "CASCADE", "NO ACTION", Arrays.asList("id_producto"), Arrays.asList("id_producto")));
        final HashSet<TableInfo.Index> _indicesDetalleCarrito = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDetalleCarrito = new TableInfo("detalle_carrito", _columnsDetalleCarrito, _foreignKeysDetalleCarrito, _indicesDetalleCarrito);
        final TableInfo _existingDetalleCarrito = TableInfo.read(db, "detalle_carrito");
        if (!_infoDetalleCarrito.equals(_existingDetalleCarrito)) {
          return new RoomOpenHelper.ValidationResult(false, "detalle_carrito(com.example.sneakervibev1.data.entidades.DetalleCarrito).\n"
                  + " Expected:\n" + _infoDetalleCarrito + "\n"
                  + " Found:\n" + _existingDetalleCarrito);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "23ea3d0f3cdf350fc4210f0b017c877e", "dc2ecc5af8887141e565c8562c7ec9d2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "usuario","categoria","producto","carrito","detalle_carrito");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `usuario`");
      _db.execSQL("DELETE FROM `categoria`");
      _db.execSQL("DELETE FROM `producto`");
      _db.execSQL("DELETE FROM `carrito`");
      _db.execSQL("DELETE FROM `detalle_carrito`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UsuarioDao.class, UsuarioDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProductoDao.class, ProductoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CarritoDao.class, CarritoDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UsuarioDao usuarioDao() {
    if (_usuarioDao != null) {
      return _usuarioDao;
    } else {
      synchronized(this) {
        if(_usuarioDao == null) {
          _usuarioDao = new UsuarioDao_Impl(this);
        }
        return _usuarioDao;
      }
    }
  }

  @Override
  public ProductoDao productoDao() {
    if (_productoDao != null) {
      return _productoDao;
    } else {
      synchronized(this) {
        if(_productoDao == null) {
          _productoDao = new ProductoDao_Impl(this);
        }
        return _productoDao;
      }
    }
  }

  @Override
  public CarritoDao carritoDao() {
    if (_carritoDao != null) {
      return _carritoDao;
    } else {
      synchronized(this) {
        if(_carritoDao == null) {
          _carritoDao = new CarritoDao_Impl(this);
        }
        return _carritoDao;
      }
    }
  }
}
