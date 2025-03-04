package net.devemperor.wristassist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import net.devemperor.wristassist.R;
import net.devemperor.wristassist.database.UsageModel;
import net.devemperor.wristassist.util.WristAssistUtil;

import java.util.List;
import java.util.Locale;


public class UsageAdapter extends ArrayAdapter<UsageModel> {
    final Context context;
    final List<UsageModel> objects;

    public UsageAdapter(@NonNull Context context, @NonNull List<UsageModel> objects) {
        super(context, -1, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {
        View listItem = LayoutInflater.from(context).inflate(R.layout.item_usage, parent, false);

        UsageModel dataProvider = objects.get(position);

        TextView modelNameTv = listItem.findViewById(R.id.item_usage_model_tv);
        modelNameTv.setText(WristAssistUtil.translate(context, dataProvider.getModelName()));

        TextView tokensTv = listItem.findViewById(R.id.item_usage_tokens_tv);
        if (dataProvider.getModelName().startsWith("gpt") || dataProvider.getModelName().startsWith("o1")) {
            tokensTv.setText(context.getString(R.string.wristassist_token_usage,
                    String.format(Locale.getDefault(), "%,d", dataProvider.getTokens())));
        } else if (dataProvider.getModelName().startsWith("dall-e")) {
            tokensTv.setText(context.getString(R.string.wristassist_images_count,
                    String.format(Locale.getDefault(), "%,d", dataProvider.getTokens())));
        } else if (dataProvider.getModelName().startsWith("whisper")) {
            tokensTv.setText(context.getString(R.string.wristassist_whisper_count,
                    dataProvider.getTokens() / 60, dataProvider.getTokens() % 60));
        }

        TextView costTv = listItem.findViewById(R.id.item_usage_cost_tv);
        costTv.setText(context.getString(R.string.wristassist_estimated_cost,
                String.format(Locale.getDefault(), "%,.2f", dataProvider.getCost())));

        return listItem;
    }
}