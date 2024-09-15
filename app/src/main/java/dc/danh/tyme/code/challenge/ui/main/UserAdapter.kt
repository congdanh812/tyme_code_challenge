package dc.danh.tyme.code.challenge.ui.main

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dc.danh.tyme.code.challenge.data.local.UserEntity
import dc.danh.tyme.code.challenge.databinding.ItemUserBinding

/**
 * Adapter for displaying a list of users in a RecyclerView.
 * Uses ListAdapter to efficiently handle data changes.
 *
 * @param onClick A lambda function that is called when a user item is clicked.
 * The function receives the login name of the clicked user.
 */
class UserAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<UserEntity, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder for a single user item in the RecyclerView.
     *
     * @param binding The ViewBinding object for the item layout.
     */
    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            binding.tvLoginName.text = user.login
            binding.tvLandingUrl.text = user.htmlUrl
            Glide.with(binding.ivAvatar.context).load(user.avatarUrl).circleCrop()
                .into(binding.ivAvatar)
            binding.tvLandingUrl.setOnClickListener {
                // Create an Intent to open the URL
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(user.htmlUrl)
                }
                binding.tvLandingUrl.context.startActivity(intent)
            }
            binding.root.setOnClickListener { onClick(user.login) }
        }
    }
}

/**
 * A [DiffUtil.ItemCallback] for [UserEntity] objects.
 *
 * This class is used to determine if two user entities are the same item and if their contents are the same.
 * It is used by [DiffUtil] to calculate the difference between two lists of users.
 */
class UserDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean =
        oldItem.login == newItem.login

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean =
        oldItem == newItem
}
