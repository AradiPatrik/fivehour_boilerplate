package com.aradipatrik.singlemoduleboilerplate.data.local

import androidx.room.*
import com.aradipatrik.singlemoduleboilerplate.data.remote.RemoteRepo
import io.reactivex.Completable
import io.reactivex.Flowable

@Entity(tableName = "repo")
data class LocalRepo(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "name") val name: String,
        val isFavorited: Boolean
) {
    companion object {
        fun fromRemote(remote: RemoteRepo) =
            LocalRepo(remote.id, remote.name, false)
    }
}

@Dao
interface RepoDao {
    @Query("SELECT * FROM repo")
    fun getRepos(): Flowable<List<LocalRepo>>

    @Query("SELECT * FROM repo WHERE isFavorited is 1")
    fun getFavoriteRepos(): Flowable<List<LocalRepo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(localRepos: List<LocalRepo>): Completable
}
