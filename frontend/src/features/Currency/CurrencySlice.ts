import UtilsService from '@app/service/UtilsService';
import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import type { CurrencyState } from './CurrencyState';

const initialState: CurrencyState = {
  activeCurrency: 'eur',
  currencies: [],
  loading: true,
};

export const fetchCurrencies = createAsyncThunk(
  'language/fetchCurrencies',
  async () => {
    const data = await UtilsService.getCurrencies();
    return data;
  },
);

const currencySlice = createSlice({
  name: 'currency',
  initialState,
  reducers: {
    setActiveCurrency: (state, action: PayloadAction<string>) => {
      state.activeCurrency = action.payload;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(
      fetchCurrencies.fulfilled,
      (state, action: PayloadAction<string[]>) => {
        state.currencies.push(...action.payload);
        state.loading = false;
      },
    );
  },
});

export const { setActiveCurrency } = currencySlice.actions;
export default currencySlice.reducer;
