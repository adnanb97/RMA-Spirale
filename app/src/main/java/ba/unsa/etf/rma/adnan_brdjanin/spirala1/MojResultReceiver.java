package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class MojResultReceiver extends ResultReceiver {
    public MojResultReceiver(Handler handler) {
        super(handler);
    }
    private Receiver mReceiver;
    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver{
        public void onReceiveResult(int resultCode, Bundle resultData);
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
