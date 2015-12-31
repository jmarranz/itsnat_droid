package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.text.InputType;

import org.itsnat.droid.impl.util.MapSmart;

/**
 * Created by jmarranz on 28/07/14.
 */
public class InputTypeUtil
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create(32);
    static
    {
        nameValueMap.put("none", 0x00000000);
        nameValueMap.put("text", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        nameValueMap.put("textCapCharacters",InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        nameValueMap.put("textCapWords",InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        nameValueMap.put("textCapSentences",InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        nameValueMap.put("textAutoCorrect",InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        nameValueMap.put("textAutoComplete",InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        nameValueMap.put("textMultiLine",InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        nameValueMap.put("textImeMultiLine",InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        nameValueMap.put("textNoSuggestions",InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        nameValueMap.put("textUri",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        nameValueMap.put("textEmailAddress",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        nameValueMap.put("textEmailSubject",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        nameValueMap.put("textShortMessage",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        nameValueMap.put("textLongMessage",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        nameValueMap.put("textPersonName",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        nameValueMap.put("textPostalAddress",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
        nameValueMap.put("textPassword",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        nameValueMap.put("textVisiblePassword",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        nameValueMap.put("textWebEditText",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
        nameValueMap.put("textFilter",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_FILTER);
        nameValueMap.put("textPhonetic",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PHONETIC);
        nameValueMap.put("textWebEmailAddress",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        nameValueMap.put("textWebPassword",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        nameValueMap.put("number",InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        nameValueMap.put("numberSigned",InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        nameValueMap.put("numberDecimal",InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        nameValueMap.put("numberPassword",InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        nameValueMap.put("phone",InputType.TYPE_CLASS_PHONE);
        nameValueMap.put("datetime",InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_NORMAL);
        nameValueMap.put("date",InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
        nameValueMap.put("time",InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);
    }
}
