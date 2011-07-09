/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.contacts.calllog;

import com.android.contacts.PhoneCallDetails;
import com.android.contacts.PhoneCallDetailsHelper;
import com.android.internal.telephony.CallerInfo;

import android.graphics.drawable.Drawable;
import android.provider.CallLog.Calls;
import android.text.TextUtils;
import android.view.View;

/**
 * Helper class to fill in the views of a call log entry.
 */
/*package*/ class CallLogListItemHelper {
    /** Helper for populating the details of a phone call. */
    private final PhoneCallDetailsHelper mPhoneCallDetailsHelper;
    /** Icon for the call action. */
    private final Drawable mCallDrawable;
    /** Icon for the play action. */
    private final Drawable mPlayDrawable;

    /**
     * Creates a new helper instance.
     *
     * @param phoneCallDetailsHelper used to set the details of a phone call
     * @param callDrawable used to render the call button, for calling back a person
     * @param playDrawable used to render the play button, for playing a voicemail
     */
    public CallLogListItemHelper(PhoneCallDetailsHelper phoneCallDetailsHelper,
            Drawable callDrawable, Drawable playDrawable) {
        mPhoneCallDetailsHelper = phoneCallDetailsHelper;
        mCallDrawable = callDrawable;
        mPlayDrawable = playDrawable;
    }

    /**
     * Sets the name, label, and number for a contact.
     *
     * @param views the views to populate
     * @param details the details of a phone call needed to fill in the data
     * @param useIcons whether to use icons to show the type of the call
     */
    public void setPhoneCallDetails(CallLogListItemViews views, PhoneCallDetails details,
            boolean useIcons) {
        mPhoneCallDetailsHelper.setPhoneCallDetails(views.phoneCallDetailsViews, details, useIcons);
        if (views.callView != null) {
            views.callView.setImageDrawable(
                    details.callType == Calls.VOICEMAIL_TYPE ? mPlayDrawable : mCallDrawable);
            views.callView.setVisibility(
                    canPlaceCallsTo(details.number) ? View.VISIBLE : View.INVISIBLE);
        }
    }

    /** Returns true if it is possible to place a call to the given number. */
    public boolean canPlaceCallsTo(CharSequence number) {
        return !(TextUtils.isEmpty(number)
                || number.equals(CallerInfo.UNKNOWN_NUMBER)
                || number.equals(CallerInfo.PRIVATE_NUMBER)
                || number.equals(CallerInfo.PAYPHONE_NUMBER));
    }
}
