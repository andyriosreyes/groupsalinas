package com.andy.rios.elektra.ui.model

import androidx.annotation.DrawableRes
import com.andy.rios.elektra.R

class DialogModel(
    @DrawableRes
    var icono: Int = R.drawable.ic_warning,

    var subtitle: String = "",

    var close: Boolean = true,

    var visibilityNotWarning : Boolean = true,


)
