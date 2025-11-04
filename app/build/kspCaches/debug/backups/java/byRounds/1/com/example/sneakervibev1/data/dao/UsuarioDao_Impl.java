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
import com.example.sneakervibev1.data.entidades.Usuario;
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
public final class UsuarioDao_Impl implements UsuarioDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Usuario> __insertionAdapterOfUsuario;

  public UsuarioDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUsuario = new EntityInsertionAdapter<Usuario>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `usuario` (`id_usuario`,`nombre`,`correo`,`password`,`rol`,`activo`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Usuario entity) {
        statement.bindLong(1, entity.getId_usuario());
        statement.bindString(2, entity.getNombre());
        statement.bindString(3, entity.getCorreo());
        statement.bindString(4, entity.getPassword());
        statement.bindString(5, entity.getRol());
        final int _tmp = entity.getActivo() ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
  }

  @Override
  public Object insertarUsuario(final Usuario usuario,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUsuario.insert(usuario);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object login(final String correo, final String password,
      final Continuation<? super Usuario> $completion) {
    final String _sql = "SELECT * FROM usuario WHERE correo = ? AND password = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, correo);
    _argIndex = 2;
    _statement.bindString(_argIndex, password);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Usuario>() {
      @Override
      @Nullable
      public Usuario call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdUsuario = CursorUtil.getColumnIndexOrThrow(_cursor, "id_usuario");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfRol = CursorUtil.getColumnIndexOrThrow(_cursor, "rol");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final Usuario _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId_usuario;
            _tmpId_usuario = _cursor.getInt(_cursorIndexOfIdUsuario);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpCorreo;
            _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final String _tmpRol;
            _tmpRol = _cursor.getString(_cursorIndexOfRol);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _result = new Usuario(_tmpId_usuario,_tmpNombre,_tmpCorreo,_tmpPassword,_tmpRol,_tmpActivo);
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

  @Override
  public Object obtenerUsuarios(final Continuation<? super List<Usuario>> $completion) {
    final String _sql = "SELECT * FROM usuario";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Usuario>>() {
      @Override
      @NonNull
      public List<Usuario> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdUsuario = CursorUtil.getColumnIndexOrThrow(_cursor, "id_usuario");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfCorreo = CursorUtil.getColumnIndexOrThrow(_cursor, "correo");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfRol = CursorUtil.getColumnIndexOrThrow(_cursor, "rol");
          final int _cursorIndexOfActivo = CursorUtil.getColumnIndexOrThrow(_cursor, "activo");
          final List<Usuario> _result = new ArrayList<Usuario>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Usuario _item;
            final int _tmpId_usuario;
            _tmpId_usuario = _cursor.getInt(_cursorIndexOfIdUsuario);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpCorreo;
            _tmpCorreo = _cursor.getString(_cursorIndexOfCorreo);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final String _tmpRol;
            _tmpRol = _cursor.getString(_cursorIndexOfRol);
            final boolean _tmpActivo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActivo);
            _tmpActivo = _tmp != 0;
            _item = new Usuario(_tmpId_usuario,_tmpNombre,_tmpCorreo,_tmpPassword,_tmpRol,_tmpActivo);
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
