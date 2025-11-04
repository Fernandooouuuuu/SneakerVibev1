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
import com.example.sneakervibev1.data.entidades.Carrito;
import com.example.sneakervibev1.data.entidades.DetalleCarrito;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class CarritoDao_Impl implements CarritoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Carrito> __insertionAdapterOfCarrito;

  private final EntityInsertionAdapter<DetalleCarrito> __insertionAdapterOfDetalleCarrito;

  private final SharedSQLiteStatement __preparedStmtOfEliminarDetalle;

  public CarritoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCarrito = new EntityInsertionAdapter<Carrito>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `carrito` (`id_carrito`,`id_usuario`,`creado_en`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Carrito entity) {
        statement.bindLong(1, entity.getId_carrito());
        statement.bindLong(2, entity.getId_usuario());
        statement.bindString(3, entity.getCreado_en());
      }
    };
    this.__insertionAdapterOfDetalleCarrito = new EntityInsertionAdapter<DetalleCarrito>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `detalle_carrito` (`id_detalle`,`id_carrito`,`id_producto`,`cantidad`,`subtotal`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DetalleCarrito entity) {
        statement.bindLong(1, entity.getId_detalle());
        statement.bindLong(2, entity.getId_carrito());
        statement.bindLong(3, entity.getId_producto());
        statement.bindLong(4, entity.getCantidad());
        statement.bindDouble(5, entity.getSubtotal());
      }
    };
    this.__preparedStmtOfEliminarDetalle = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM detalle_carrito WHERE id_detalle = ?";
        return _query;
      }
    };
  }

  @Override
  public Object crearCarrito(final Carrito carrito, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCarrito.insertAndReturnId(carrito);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object agregarDetalle(final DetalleCarrito detalle,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDetalleCarrito.insert(detalle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object eliminarDetalle(final int idDetalle, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfEliminarDetalle.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, idDetalle);
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
          __preparedStmtOfEliminarDetalle.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerDetalles(final int idCarrito,
      final Continuation<? super List<DetalleCarrito>> $completion) {
    final String _sql = "SELECT * FROM detalle_carrito WHERE id_carrito = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idCarrito);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DetalleCarrito>>() {
      @Override
      @NonNull
      public List<DetalleCarrito> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdDetalle = CursorUtil.getColumnIndexOrThrow(_cursor, "id_detalle");
          final int _cursorIndexOfIdCarrito = CursorUtil.getColumnIndexOrThrow(_cursor, "id_carrito");
          final int _cursorIndexOfIdProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "id_producto");
          final int _cursorIndexOfCantidad = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidad");
          final int _cursorIndexOfSubtotal = CursorUtil.getColumnIndexOrThrow(_cursor, "subtotal");
          final List<DetalleCarrito> _result = new ArrayList<DetalleCarrito>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DetalleCarrito _item;
            final int _tmpId_detalle;
            _tmpId_detalle = _cursor.getInt(_cursorIndexOfIdDetalle);
            final int _tmpId_carrito;
            _tmpId_carrito = _cursor.getInt(_cursorIndexOfIdCarrito);
            final int _tmpId_producto;
            _tmpId_producto = _cursor.getInt(_cursorIndexOfIdProducto);
            final int _tmpCantidad;
            _tmpCantidad = _cursor.getInt(_cursorIndexOfCantidad);
            final double _tmpSubtotal;
            _tmpSubtotal = _cursor.getDouble(_cursorIndexOfSubtotal);
            _item = new DetalleCarrito(_tmpId_detalle,_tmpId_carrito,_tmpId_producto,_tmpCantidad,_tmpSubtotal);
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
