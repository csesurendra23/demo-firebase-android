/*
 * Copyright (c) 2015-2018, Virgil Security, Inc.
 *
 * Lead Maintainer: Virgil Security Inc. <support@virgilsecurity.com>
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     (1) Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *
 *     (2) Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *     (3) Neither the name of virgil nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.android.virgilsecurity.virgilonfire.ui.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.android.virgilsecurity.virgilonfire.R;
import com.android.virgilsecurity.virgilonfire.ui.base.BaseActivityDi;
import com.android.virgilsecurity.virgilonfire.ui.chat.ChatControlActivity;
import com.android.virgilsecurity.virgilonfire.util.UiUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

/**
 * . _  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    4/13/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */
public final class LogInActivity extends BaseActivityDi implements HasFragmentInjector {

    @Inject protected DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject protected FirebaseAuth firebaseAuth;

    public static void start(Activity from) {
        from.startActivity(new Intent(from, LogInActivity.class));
    }

    public static void startWithFinish(Activity from) {
        from.startActivity(new Intent(from, LogInActivity.class));
        from.finish();
    }

    public static void startClearTop(Activity from) {
        from.startActivity(new Intent(from, LogInActivity.class)
                                   .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                     | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override protected int getLayout() {
        return R.layout.activity_log_in;
    }

    @Override protected void postButterInit() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null)
            startChatControlActivity(user.getEmail().toLowerCase().split("@")[0]);

        UiUtils.replaceFragmentNoTag(getFragmentManager(),
                                     R.id.flBaseContainer,
                                     LogInFragment.newInstance());
    }

    @Override public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public void startChatControlActivity(String username) {
        ChatControlActivity.startWithFinish(this, username);
    }
}
