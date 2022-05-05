package com.wk.mediate.base

import com.wk.mediate.R

class TransitionAnim(val inAnim : Int, val outAnim : Int) {
}

enum class Transition {
    None {
        override fun show(): TransitionAnim {
            return TransitionAnim(R.anim.none, R.anim.none)
        }

        override fun dismiss(): TransitionAnim {
            return TransitionAnim(R.anim.fade_out, R.anim.fade_out)
        }
    },
    Push {
        override fun show(): TransitionAnim {
            return TransitionAnim(R.anim.slide_in_right, R.anim.none)
        }

        override fun dismiss(): TransitionAnim {
            return TransitionAnim(R.anim.none, R.anim.slide_out_right)
        }
    },
    Modal {
        override fun show(): TransitionAnim {
            return TransitionAnim(R.anim.slide_up_in, R.anim.none)
        }

        override fun dismiss(): TransitionAnim {
            return TransitionAnim(R.anim.slide_up_in, R.anim.slide_down_out)
        }
    },
    Replace {
        override fun show(): TransitionAnim {
            return TransitionAnim(R.anim.fade_in, R.anim.none)
        }

        override fun dismiss(): TransitionAnim {
            return None.dismiss()
        }
    },
    ReplaceNext {
        override fun show(): TransitionAnim {
            return TransitionAnim(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        override fun dismiss(): TransitionAnim {
            return None.dismiss()
        }
    },
    ReplacePrev {
        override fun show(): TransitionAnim {
            return TransitionAnim(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        override fun dismiss(): TransitionAnim {
            return None.dismiss()
        }

    };

    abstract fun show(): TransitionAnim
    abstract fun dismiss(): TransitionAnim
}