package com.example.nfc_studentapp;

import android.nfc.NdefRecord;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

/**
 * A representation of an NFC Forum "Smart Poster".
 */
public class SmartPoster implements ParsedNdefRecord {

    private static Locale Iterables;
    private static Object Charsets;
    /**
     * NFC Forum Smart Poster Record Type Definition section 3.2.1.
     *
     * "The Title record for the service (there can be many of these in
     * different languages, but a language MUST NOT be repeated). This record is
     * optional."
     */
    private final TextRecord mTitleRecord;

    /**
     * NFC Forum Smart Poster Record Type Definition section 3.2.1.
     *
     * "The URI record. This is the core of the Smart Poster, and all other
     * records are just metadata about this record. There MUST be one URI record
     * and there MUST NOT be more than one."
     */
    private final RecommendedAction mUriRecord;

    /**
     * NFC Forum Smart Poster Record Type Definition section 3.2.1.
     *
     * "The Action record. This record describes how the service should be
     * treated. For example, the action may indicate that the device should save
     * the URI as a bookmark or open a browser. The Action record is optional.
     * If it does not exist, the device may decide what to do with the service.
     * If the action record exists, it should be treated as a strong suggestion;
     * the UI designer may ignore it, but doing so will induce a different user
     * experience from device to device."
     */
    private final RecommendedAction mAction;

    /**
     * NFC Forum Smart Poster Record Type Definition section 3.2.1.
     *
     * "The Type record. If the URI references an external entity (e.g., via a
     * URL), the Type record may be used to declare the MIME type of the entity.
     * This can be used to tell the mobile device what kind of an object it can
     * expect before it opens the connection. The Type record is optional."
     */
    private final String mType;

    public SmartPoster(RecommendedAction uri, TextRecord title, RecommendedAction action, String type) {
        mUriRecord = Preconditions.checkNotNull(uri);
        mTitleRecord = title;
        mAction = Preconditions.checkNotNull(action);
        mType = type;
    }

    public RecommendedAction getUriRecord() {
        return mUriRecord;
    }

    /**
     * Returns the title of the smart poster. This may be {@code null}.
     */
    public TextRecord getTitle() {
        return mTitleRecord;
    }




    public static boolean isPoster(NdefRecord record) {
        try {
            parse(record);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static void parse(NdefRecord record) {

    }

    public String str() {
        if (mTitleRecord != null) {
            return mTitleRecord.str() + "\n" + mUriRecord.str();
        } else {
            return mUriRecord.str();
        }
    }

    /**
     * Returns the first element of {@code elements} which is an instance of
     * {@code type}, or {@code null} if no such element exists.
     */


    public enum RecommendedAction {
        UNKNOWN((byte) -1), DO_ACTION((byte) 0), SAVE_FOR_LATER((byte) 1), OPEN_FOR_EDITING(
                (byte) 2);


        public static Map LOOKUP;
        private final byte mAction;

        private RecommendedAction(byte val) {
            this.mAction = val;
        }

        private byte getByte() {
            return mAction;
        }

        public String str() {
            return "";
        }
    }

    private static NdefRecord getByType(byte[] type, NdefRecord[] records) {
        for (NdefRecord record : records) {
            if (Arrays.equals(type, record.getType())) {
                return record;
            }
        }
        return null;
    }

    private static final byte[] ACTION_RECORD_TYPE = new byte[] {'a', 'c', 't'};

    private static RecommendedAction parseRecommendedAction(NdefRecord[] records) {
        NdefRecord record = getByType(ACTION_RECORD_TYPE, records);
        if (record == null) {
            return RecommendedAction.UNKNOWN;
        }
        byte action = record.getPayload()[0];
        if (RecommendedAction.LOOKUP.containsKey(action)) {
            return (RecommendedAction) RecommendedAction.LOOKUP.get(action);
        }
        return RecommendedAction.UNKNOWN;
    }

    private static final byte[] TYPE_TYPE = new byte[] {'t'};




    private static class Builder<T, T1> {
    }
}
