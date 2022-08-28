import UtilsService from '@app/service/UtilsService';
import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import type { LanguageState } from './LanguageState';

const initialState: LanguageState = {
  activeLang: 'en',
  languages: [],
  loading: true,
};

export const fetchLanguages = createAsyncThunk(
  'language/fetchLanguages',
  async (thunkApi) => {
    const data = await UtilsService.getLanguages();
    return data;
  },
);

const languageSlice = createSlice({
  name: 'language',
  initialState,
  reducers: {
    setActiveLanguage: (state, action: PayloadAction<string>) => {
      state.activeLang = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchLanguages.fulfilled, (state, action) => {
      state.languages.push(...action.payload);
      state.loading = false;
    });
  },
});

export const { setActiveLanguage } = languageSlice.actions;
export default languageSlice.reducer;
