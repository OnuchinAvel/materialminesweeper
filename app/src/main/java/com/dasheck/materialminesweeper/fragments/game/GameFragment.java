package com.dasheck.materialminesweeper.fragments.game;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import butterknife.Bind;
import com.dasheck.materialminesweeper.R;
import com.dasheck.materialminesweeper.adapters.BaseAdapter;
import com.dasheck.materialminesweeper.adapters.TileListAdapter;
import com.dasheck.materialminesweeper.annotations.Layout;
import com.dasheck.materialminesweeper.fragments.BaseFragment;
import com.dasheck.materialminesweeper.layoutmanagers.FixedGridLayoutManager;
import com.dasheck.materialminesweeper.utilities.Utilities;
import com.dasheck.model.models.Tile;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by s.neidig on 17/01/16.
 */
@Layout(R.layout.fragment_test) public class GameFragment extends BaseFragment
    implements GameView, BaseAdapter.OnItemClickedListener, BaseAdapter.OnItemLongClickedListener {

  @Bind(R.id.tileMapContainer) LinearLayout tileMapContainer;
  @Bind(R.id.tileMap) RecyclerView tileMap;

  @Inject GamePresenter presenter;
  @Inject TileListAdapter adapter;

  private FixedGridLayoutManager gridLayoutManager;

  @Override public void initializeViews() {
    setPresenter(presenter);

    gridLayoutManager = new FixedGridLayoutManager();

    tileMap.setAdapter(adapter);
    tileMap.setHasFixedSize(true);
    tileMap.setLayoutManager(gridLayoutManager);
    adapter.setOnItemClickedListener(this);
    adapter.setOnItemLongClickedListener(this);
  }

  @Override public void setTiles(List<Tile> tiles) {
    adapter.clear();
    adapter.addAll(tiles);
  }

  @Override public void setDimension(int width, int height) {
    gridLayoutManager.setTotalColumnCount(width);
  }

  @Override public void repositionGrid(int columns, int rows) {
    float sizeOfTile = getContext().getResources().getDimension(R.dimen.tile_size);
    float width = columns * sizeOfTile;
    float height = rows * sizeOfTile;
    Pair<Integer, Integer> displaySize = Utilities.getWindowDimensions(getContext());

    int left = (int) Utilities.convertDpToPixel(16, getContext());
    int right = (int) Utilities.convertDpToPixel(16, getContext());
    int top = (int) Utilities.convertDpToPixel(16, getContext());
    int bottom = (int) Utilities.convertDpToPixel(16, getContext());

    if (width < displaySize.first - (left + right)) {
      int difference = (int) (displaySize.first - width);
      left = difference / 2;
      right = difference / 2;
    }

    if (height < displaySize.second - (top + bottom)) {
      int difference = (int) (displaySize.second - height);
      top = difference / 2;
      bottom = difference / 2;
    }

    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tileMap.getLayoutParams();

    params.setMargins(left, top, right, bottom);
    tileMap.setLayoutParams(params);
  }

  @Override public void onItemClicked(int position) {
    presenter.revealTile(adapter.get(position));
  }

  @Override public void onItemLongClicked(int position) {
    presenter.markTile(adapter.get(position));
  }
}
