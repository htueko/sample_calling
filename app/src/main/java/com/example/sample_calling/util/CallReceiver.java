package com.example.sample_calling.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {
  private Context ctx;
  public static final String TAG = "CallReceiver";

  @Override
  public void onReceive(Context context, Intent intent) {
    ctx = context;
    OutgoingCallListener outgoingCallListener = new OutgoingCallListener();
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
      telephonyManager.listen(outgoingCallListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
  }

  class OutgoingCallListener extends PhoneStateListener {
    public boolean isRinging;

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
      switch (state) {
        case TelephonyManager.CALL_STATE_RINGING:
          Log.i(TAG, "Ringing");
          isRinging = true;
          Toast.makeText(ctx, "Phone is ringing", Toast.LENGTH_SHORT).show();
          break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
          Log.i(TAG, "OffHook");
          Toast.makeText(ctx, "Calling", Toast.LENGTH_SHORT).show();
          isRinging = true;
          break;
        case TelephonyManager.CALL_STATE_IDLE:
          Log.i(TAG, "Idle");
          if (isRinging) {
            Log.i(TAG, "Call is reject");
            Toast.makeText(ctx, "Call is reject by receiver", Toast.LENGTH_SHORT).show();
          }
          isRinging = true;
          break;
      }
    }
  }
}
