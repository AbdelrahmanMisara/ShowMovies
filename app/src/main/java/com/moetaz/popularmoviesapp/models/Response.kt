package com.moetaz.popularmoviesapp.models

import android.os.Parcel
import android.os.Parcelable


data class Response(
    val page: Int,
    val results : List<Movie>,
    val total_pages: Int,
    val total_results: Int
)


data class Movie(
    var isFav : Boolean,
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        1 == source.readInt(),
        1 == source.readInt(),
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readDouble(),
        source.readString(),
        source.readString(),
        source.readString(),
        1 == source.readInt(),
        source.readDouble(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (isFav) 1 else 0))
        writeInt((if (adult) 1 else 0))
        writeString(backdrop_path)
        writeList(genre_ids)
        writeInt(id)
        writeString(original_language)
        writeString(original_title)
        writeString(overview)
        writeDouble(popularity)
        writeString(poster_path)
        writeString(release_date)
        writeString(title)
        writeInt((if (video) 1 else 0))
        writeDouble(vote_average)
        writeInt(vote_count)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}

