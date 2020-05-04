package com.example.nfc_studentapp;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.nfc.NdefRecord;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

/**
 * A parsed record containing a Uri.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class UriRecord implements ParsedNdefRecord {

    private static final String TAG = "UriRecord";

    public static final String RECORD_TYPE = "UriRecord";

    /**
     * NFC Forum "URI Record Type Definition"
     * <p>
     * This is a mapping of "URI Identifier Codes" to URI string prefixes,
     * per section 3.2.2 of the NFC Forum URI Record Type Definition document.
     */
    private final Uri mUri;

    @SuppressLint("RestrictedApi")
    public UriRecord(Uri uri) {
        this.mUri = Preconditions.checkNotNull(uri);
    }

    public String str() {
        return mUri.toString();
    }

    public Uri getUri() {
        return mUri;
    }

    /**
     * Convert {@link NdefRecord} into a {@link Uri}.
     * This will handle both TNF_WELL_KNOWN / RTD_URI and TNF_ABSOLUTE_URI.
     *
     * @throws IllegalArgumentException if the NdefRecord is not a record
     *         containing a URI.
     */
    public static UriRecord parse(NdefRecord record) {
        short tnf = record.getTnf();
        if (tnf == NdefRecord.TNF_WELL_KNOWN) {
            return parseWellKnown(record);
        } else if (tnf == NdefRecord.TNF_ABSOLUTE_URI) {
            return parseAbsolute(record);
        }
        throw new IllegalArgumentException("Unknown TNF " + tnf);
    }

    /** Parse and absolute URI record */
    private static UriRecord parseAbsolute(NdefRecord record) {
        byte[] payload = record.getPayload();
        Uri uri = Uri.parse(new String(payload, Charset.forName("UTF-8")));
        return new UriRecord(uri);
    }

    /** Parse an well known URI record
     * @return*/
    @SuppressLint("RestrictedApi")
    private static UriRecord parseWellKnown(NdefRecord record) {
        Preconditions.checkArgument(Arrays.equals(record.getType(), NdefRecord.RTD_URI));
        byte[] payload = record.getPayload();
        /*
         * payload[0] contains the URI Identifier Code, per the
         * NFC Forum "URI Record Type Definition" section 3.2.2.
         *
         * payload[1]...payload[payload.length - 1] contains the rest of
         * the URI.
         */

        return null;
    }

    public static boolean isUri(NdefRecord record) {
        try {
            parse(record);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static final byte[] EMPTY = new byte[0];


    private static class ImmutableBiMap {
        public static Map<Byte, String> builder() {
            return builder();
        }
    }

    private static class BiMap<T, T1> {
    }
}


