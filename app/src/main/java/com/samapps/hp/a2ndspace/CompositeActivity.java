package com.samapps.hp.a2ndspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.samapps.hp.a2ndspace.category.categoryModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;

import java.util.ArrayList;
import java.util.List;

public class CompositeActivity extends AppCompatActivity {
    RendererRecyclerViewAdapter adapter;

    RendererRecyclerViewAdapter mRendererRecyclerViewAdapter;
    RecyclerView mRecyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite);

        mRecyclerview = (RecyclerView) findViewById(R.id.category_recycler_view);

        adapter = new RendererRecyclerViewAdapter();
        adapter.registerRenderer(getChildItemViewBinder());
        adapter.setItems(getParentItems());
        adapter.notifyDataSetChanged();

        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private ViewBinder getChildItemViewBinder() {
        return new ViewBinder<>(
                R.layout.category_item,
                categoryModel.class,
                (model, finder, payloads) -> finder
                        .setText(R.id.title, model.getName())
        );
    }

    private List<DefaultCompositeViewModel> getParentItems() {
        ArrayList<DefaultCompositeViewModel> parents = new ArrayList();
        for (int i = 0; i < 10; i++) {
            ArrayList<categoryModel> children = new ArrayList();
            for (int j = 0; j < 10; j++) {
                children.add(new categoryModel("samuel"));
            }
            parents.add(new DefaultCompositeViewModel(children));
        }
        return parents;
    }
}
