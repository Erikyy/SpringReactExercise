import { createSlice, type PayloadAction } from '@reduxjs/toolkit';
import type { CategoryState } from './CategoryState';

const initialState: CategoryState = {
  activeCategory: 1,
};

const categorySlice = createSlice({
  name: 'category',
  initialState,
  reducers: {
    setActiveCategory: (state, action: PayloadAction<number>) => {
      state.activeCategory = action.payload;
    },
  },
});

export const { setActiveCategory } = categorySlice.actions;
export default categorySlice.reducer;
