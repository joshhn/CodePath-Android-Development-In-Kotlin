package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BookAdapter(
    private val context: Context,
    private val books: List<Book>
)
    : RecyclerView.Adapter<BookAdapter.BookViewHolder>()
{
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_books, parent, false)
        return BookViewHolder(view)
    }

    inner class BookViewHolder(val mView: View) : RecyclerView.ViewHolder(mView){
        var mItem: Book? = null
        val mBookTitle: TextView = mView.findViewById<View>(R.id.book_title) as TextView
        val mBookAuthor: TextView = mView.findViewById<View>(R.id.book_author) as TextView
        val mBookRanking: TextView = mView.findViewById<View>(R.id.ranking) as TextView
        val mBookDescription: TextView = mView.findViewById<View>(R.id.book_description) as TextView
        val mBookImage: ImageView = mView.findViewById<View>(R.id.book_image) as ImageView
        val mBookButton: Button =mView.findViewById<View>(R.id.buy_button) as Button

        override fun toString(): String {
            return mBookTitle.toString() + " '" + mBookAuthor.text + "'"
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]

        holder.mItem = book
        holder.mBookTitle.text = book.title
        holder.mBookAuthor.text = book.author
        holder.mBookDescription.text = book.description
        holder.mBookRanking.text = book.rank.toString()

        holder.mView.setOnClickListener {
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }
        }

        holder.mBookButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.amazonUrl))
            startActivity(it.context, browserIntent, null)
        }

        Glide.with(context)
            .load(book.bookImageUrl)
            .centerInside()
            .into(holder.mBookImage)
    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return books.size
    }

    interface OnListFragmentInteractionListener {
        fun onItemClick(item: Book)
    }
}