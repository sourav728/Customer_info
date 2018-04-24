package com.example.tvd.customer_info;

import com.akexorcist.googledirection.model.Direction;

/**
 * Created by TVD on 4/24/2018.
 */

interface DirectionCallback {
    void onDirectionSuccess(Direction direction, String rawBody);

    void onDirectionFailure(Throwable t);
}
