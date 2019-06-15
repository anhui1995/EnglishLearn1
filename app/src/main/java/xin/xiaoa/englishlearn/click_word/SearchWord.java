package xin.xiaoa.englishlearn.click_word;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

import xin.xiaoa.englishlearn.service.ELApplication;

public class SearchWord {
    private Context context;
    private String reg = "[^a-zA-Z]";
    private AutoCompleteTextView edit;

    public SearchWord(Context context) {
        this.context = context;
        showDialog();
    }




    void search(String word){
        new ClickWordDialog(context,word);
    }



    void showDialog()
    {
        edit = new AutoCompleteTextView(context);
        edit.addTextChangedListener(new myTextWatcher());
        edit.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
            }
            @Override
            protected char[] getAcceptedChars() {
                char[] ac = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
                return ac;
            }
        });


        edit.setHint("输入要查询的单词");
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("单词查询");//窗口名
        dialog.setView(edit);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                String strWord = edit.getText().toString().replaceAll(reg,"");
                if(!"".equals(strWord)) search(strWord);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                // TODO Auto-generated method stub
            }
        });

        dialog.show();

    }




    class myTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("afterTextChanged："+s.toString());
            // 必须将english字段的别名设为_id
            SQLiteDatabase db = ELApplication.getDb();
            Cursor cursor = db.rawQuery(
                    "select word as _id from ONLY_WORD where word like ?",
                    new String[] { s.toString() + "%" });
            DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(context,
                    cursor, true);

            try{
                edit.setAdapter(dictionaryAdapter);
            }
            catch (Exception e){
                System.out.println("actvWord大问题："+e);
            }
        }
    }

    public class DictionaryAdapter extends CursorAdapter {
        private LayoutInflater layoutInflater;

        public CharSequence convertToString(Cursor cursor) {
            return cursor == null ? "" : cursor.getString(cursor
                    .getColumnIndex("_id"));
        }

        private void setView(View view, Cursor cursor) {
            TextView tvWordItem = (TextView) view;
            tvWordItem.setText(cursor.getString(cursor.getColumnIndex("_id")));
            tvWordItem.setPadding(15, 10, 10, 15);
            tvWordItem.setTextSize(18);
        }

        public DictionaryAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // TODO Auto-generated method stub
            setView(view, cursor);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = new TextView(context);
            setView(view, cursor);
            return view;
        }

    }
}
