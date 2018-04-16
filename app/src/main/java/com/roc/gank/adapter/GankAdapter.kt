package com.roc.gank.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.roc.gank.R
import com.roc.gank.entity.GankBean
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by 赖鹏旭 on 2018/1/25.
 * 主界面列表适配器
 */
class GankAdapter : RecyclerView.Adapter<GankAdapter.ViewHolder>() {

    private var mData: ArrayList<GankBean>? = ArrayList()
    private var mListener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_gank, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.setData(mData?.get(position)!!)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var mImageView: ImageView? = itemView?.findViewById(R.id.imageView) as ImageView?
        var mDescriptionTv: TextView? = itemView?.findViewById(R.id.tv_description) as TextView
        var mAuthorTv: TextView? = itemView?.findViewById(R.id.tv_author) as TextView
        var mCreateTimeTv: TextView? = itemView?.findViewById(R.id.tv_create_time) as TextView
        var mUpdateTimeTv: TextView? = itemView?.findViewById(R.id.tv_update_time) as TextView

        init {
            itemView?.setOnClickListener {
                mListener?.onItemClick(mData?.get(adapterPosition)?.url!!)
            }
            itemView?.setOnLongClickListener {
                mListener?.onItemLongClick(mData?.get(adapterPosition)!!)
                true
            }
        }

        fun setData(gankBean: GankBean) {

            // 首图
            if (gankBean.images != null && gankBean.images?.size!! > 0) {

                Glide
                        .with(mImageView ?: return)
                        .load(gankBean.images?.get(0))
                        .thumbnail(Glide.with(mImageView ?: return).load(gankBean.images?.get(0)))
                        .apply(RequestOptions()
                                .error(R.mipmap.default_img))
                        .into(mImageView ?: return)
            } else {
                Glide
                        .with(mImageView ?: return)
                        .load("")
                        .apply(RequestOptions()
                                .skipMemoryCache(true))
                        .into(mImageView ?: return)
            }

            // 描述
            mDescriptionTv?.text = gankBean.desc

            // 作者
            if (TextUtils.isEmpty(gankBean.who)) {
                mAuthorTv?.visibility = View.GONE
            } else {
                mAuthorTv?.visibility = View.VISIBLE
                mAuthorTv?.text = gankBean.who
            }

            // 创作时间
            val createDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA).parse(gankBean.createdAt)
            val createTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(createDate)
            mCreateTimeTv?.text = createTime

            // 上传时间
            val updateDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA).parse(gankBean.publishedAt)
            val updateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(updateDate)
            mUpdateTimeTv?.text = updateTime

        }
    }

    fun initData(data: List<GankBean>?) {
        if (data != null && data.isNotEmpty()) {
            mData?.clear()
            mData?.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun update(data: List<GankBean>?) {
        if (data != null && data.isNotEmpty()) {
            mData?.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(url: String)
        fun onItemLongClick(bean: GankBean)
    }
}