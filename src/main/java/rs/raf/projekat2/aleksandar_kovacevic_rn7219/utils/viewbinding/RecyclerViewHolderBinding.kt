package rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class RecyclerViewHolderBinding<T : ViewBinding>(val viewBinding: T) :
    RecyclerView.ViewHolder(viewBinding.root)
