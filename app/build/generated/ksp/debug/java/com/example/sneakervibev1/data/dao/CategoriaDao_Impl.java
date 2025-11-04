package com.example.sneakervibev1.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.sneakervibev1.data.entidades.Categoria;
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
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CategoriaDao_Impl implements CategoriaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Categoria> __insertionAdapterOfCategoria;

  public CategoriaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCategoria = new EntityInsertionAdapter<Categoria>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `categoria` (`id_categoria`,`nombre_categoria`) VALUES (nullif(?, 0),?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Categoria entity) {
        statement.bindLong(1, entity.getId_categoria());
        statement.bindString(2, entity.getNombre_categoria());
      }
    };
  }

  @Override
  public Object insertar(final Categoria categoria, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCategoria.insertAndReturnId(categoria);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object todas(final Continuation<? super List<Categoria>> $completion) {
    final String _sql = "SELECT * FROM categoria ORDER BY nombre_categoria";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Categoria>>() {
      @Override
      @NonNull
      public List<Categoria> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "id_categoria");
          final int _cursorIndexOfNombreCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre_categoria");
          final List<Categoria> _result = new ArrayList<Categoria>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Categoria _item;
            final int _tmpId_categoria;
            _tmpId_categoria = _cursor.getInt(_cursorIndexOfIdCategoria);
            final String _tmpNombre_categoria;
            _tmpNombre_categoria = _cursor.getString(_cursorIndexOfNombreCategoria);
            _item = new Categoria(_tmpId_categoria,_tmpNombre_categoria);
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
  public Object porNombre(final String nombre, final Continuation<? super Categoria> $completion) {
    final String _sql = "SELECT * FROM categoria WHERE nombre_categoria = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, nombre);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Categoria>() {
      @Override
      @Nullable
      public Categoria call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "id_categoria");
          final int _cursorIndexOfNombreCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre_categoria");
          final Categoria _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId_categoria;
            _tmpId_categoria = _cursor.getInt(_cursorIndexOfIdCategoria);
            final String _tmpNombre_categoria;
            _tmpNombre_categoria = _cursor.getString(_cursorIndexOfNombreCategoria);
            _result = new Categoria(_tmpId_categoria,_tmpNombre_categoria);
          } else {
            _result = null;
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
