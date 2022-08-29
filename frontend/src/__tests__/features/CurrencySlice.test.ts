import CurrencyReducer, {
  setActiveCurrency,
  fetchCurrencies,
} from '@app/features/Currency/CurrencySlice';
import type { CurrencyState } from '@app/features/Currency/CurrencyState';
import UtilsService from '@app/service/UtilsService';
import { configureStore } from '@reduxjs/toolkit';

const fakeFetchData = ['eur', 'usd', 'gbp'];

describe('CurrencySlice Unit tests', () => {
  const testStore = configureStore({
    reducer: CurrencyReducer,
  });
  beforeAll(() => {
    //setup mocks
    jest
      .spyOn(UtilsService, 'getCurrencies')
      .mockImplementation(
        jest.fn(() => Promise.resolve(fakeFetchData)) as jest.Mock,
      );
  });

  test('Should return initial state', () => {
    const expectedState: CurrencyState = {
      activeCurrency: 'eur',
      currencies: [],
      loading: true,
    };
    expect(CurrencyReducer(undefined, { type: undefined })).toEqual(
      expectedState,
    );
  });

  test('Should set activeCurrency in state', () => {
    const prevState: CurrencyState = {
      activeCurrency: 'eur',
      currencies: [],
      loading: true,
    };
    const expectedState: CurrencyState = {
      activeCurrency: 'usd',
      currencies: [],
      loading: true,
    };

    expect(CurrencyReducer(prevState, setActiveCurrency('usd'))).toEqual(
      expectedState,
    );
  });

  test('Should fetch currencies and expect to be same as fakeFetchData and set loading to false', async () => {
    await testStore.dispatch(fetchCurrencies());
    const expectedState: CurrencyState = {
      activeCurrency: 'eur',
      currencies: fakeFetchData,
      loading: false,
    };
    expect(testStore.getState()).toEqual(expectedState);
  });
});
