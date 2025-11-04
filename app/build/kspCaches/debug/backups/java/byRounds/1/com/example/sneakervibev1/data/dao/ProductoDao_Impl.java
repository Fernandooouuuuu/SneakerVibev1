package com.example.sneakervibev1.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.sneakervibev1.data.entidades.Producto;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProductoDao_Impl implements ProductoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Producto> __insertionAdapterOfProducto;

  private final SharedSQLiteStatement __preparedStmtOfActualizarStock;

  public ProductoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProducto = new EntityInsertionAdapter<Producto>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `producto` (`id_producto`,`id_categoria`,`nombre_producto`,`descripcion`,`precio`,`stock`,`imagen`,`activo`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Producto entity) {
        statement.bindLong(1, entity.getId_producto());
        statement.bindLong(2, entity.getId_categoria());
        statement.bindString(3, entity.getNombre_producto());
        statement.bindString(4, entity.getDescripcion());
        statement.bindDouble(5, entity.getPrecio());
        statement.bindLong(6, entity.getStock());
        if (entity.getImagen() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getImagen());
        }
        final int _tmp = entity.getActivo() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__preparedStmtOfActualizarStock = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE producto SET stock = ? WHERE id_producto = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertar(final Producto producto, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProducto.insert(producto);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object actualizarStock(final int id, final int nuevo,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfActualizarStock.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, nuevo);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfActualizarStock.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object listar(final Continuation<? super List<Producto>> $completion) {
    final String _sql = "SELECT * FROM producto ORDER BY id_producto DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Producto>>() {
      @Override
      @NonNull
      public List<Producto> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "id_producto");
          final int _cursorIndexOfIdCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "id_categoria");
          final int _cursorIndexOfNombreProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre_producto");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrecio = CursorUtil.getColumnIndexOrThrow(_cursor, "precio");
          final int _cursorIndexOfStock = CursorUtil.getColumnIndexOrThrow(_cursor, "stock");
          final int _cursorIndexOfImagen = CursorUtil.getColumnIndexOrThrow(_cursor, "imagen");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<Producto> _result = new ArrayList<Producto>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Producto _item;
            final int _tmpId_producto;
            _tmpId_producto = _cursor.getInt(_cursorIndexOfIdProducto);
            final int _tmpId_categoria;
            _tmpId_categoria = _cursor.getInt(_cursorIndexOfIdCategoria);
            final String _tmpNombre_producto;
            _tmpNombre_producto = _cursor.getString(_cursorIndexOfNombreProducto);
            final String _tmpDescripcion;
            _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            final double _tmpPrecio;
            _tmpPrecio = _cursor.getDouble(_cursorIndexOfPrecio);
            final int _tmpStock;
            _tmpStock = _cursor.getInt(_cursorIndexOfStock);
            final String _tmpImagen;
            if (_cursor.isNull(_cursorIndexOfImagen)) {
              _tmpImagen = null;
            } else {
              _tmpImagen = _cursor.getString(_cursorIndexOfImagen);
            }
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new Producto(_tmpId_producto,_tmpId_categoria,_tmpNombre_producto,_tmpDescripcion,_tmpPrecio,_tmpStock,_tmpImagen,_tmpActivo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object listarPorCategoria(final int idCat,
      final Continuation<? super List<Producto>> $completion) {
    final String _sql = "SELECT * FROM producto WHERE id_categoria = ? ORDER BY id_producto DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idCat);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Producto>>() {
      @Override
      @NonNull
      public List<Producto> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "id_producto");
          final int _cursorIndexOfIdCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "id_categoria");
          final int _cursorIndexOfNombreProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre_producto");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrecio = CursorUtil.getColumnIndexOrThrow(_cursor, "precio");
          final int _cursorIndexOfStock = CursorUtil.getColumnIndexOrThrow(_cursor, "stock");
          final int _cursorIndexOfImagen = CursorUtil.getColumnIndexOrThrow(_cursor, "imagen");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<Producto> _result = new ArrayList<Producto>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Producto _item;
            final int _tmpId_producto;
            _tmpId_producto = _cursor.getInt(_cursorIndexOfIdProducto);
            final int _tmpId_categoria;
            _tmpId_categoria = _cursor.getInt(_cursorIndexOfIdCategoria);
            final String _tmpNombre_producto;
            _tmpNombre_producto = _cursor.getString(_cursorIndexOfNombreProducto);
            final String _tmpDescripcion;
            _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            final double _tmpPrecio;
            _tmpPrecio = _cursor.getDouble(_cursorIndexOfPrecio);
            final int _tmpStock;
            _tmpStock = _cursor.getInt(_cursorIndexOfStock);
            final String _tmpImagen;
            if (_cursor.isNull(_cursorIndexOfImagen)) {
              _tmpImagen = null;
            } else {
              _tmpImagen = _cursor.getString(_cursorIndexOfImagen);
            }
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new Producto(_tmpId_producto,_tmpId_categoria,_tmpNombre_producto,_tmpDescripcion,_tmpPrecio,_tmpStock,_tmpImagen,_tmpActivo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
