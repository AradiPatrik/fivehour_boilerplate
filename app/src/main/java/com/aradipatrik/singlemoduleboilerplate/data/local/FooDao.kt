package com.aradipatrik.singlemoduleboilerplate.data.local

import androidx.room.*
import com.aradipatrik.singlemoduleboilerplate.data.remote.RemoteFoo
import io.reactivex.Completable
import io.reactivex.Flowable

@Entity(tableName = "foo")
data class LocalFoo(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "display") val displayableAttribute: String,
    val isFavorited: Boolean
) {
    companion object {
        fun fromRemote(remote: RemoteFoo) =
            LocalFoo(remote.id, remote.displayableAttribute, false)
    }
}

@Dao
interface FooDao {
    @Query("SELECT * FROM foo")
    fun getFoos(): Flowable<List<LocalFoo>>

    @Query("SELECT * FROM foo WHERE isFavorited is 1")
    fun getFavoriteFoos(): Flowable<List<LocalFoo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(localFoos: List<LocalFoo>): Completable
}