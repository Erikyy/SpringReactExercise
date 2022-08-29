import LanguageReducer, {
  fetchLanguages,
  setActiveLanguage,
} from '@app/features/Language/LanguageSlice';
import UtilsService from '@app/service/UtilsService';
import { LanguageState } from '@app/features/Language/LanguageState';
import { configureStore } from '@reduxjs/toolkit';

const fakeFetchData = ['en', 'et'];

describe('LanguageSlice unit tests', () => {
  const testStore = configureStore({
    reducer: LanguageReducer,
  });
  beforeAll(() => {
    //setup mocks
    jest
      .spyOn(UtilsService, 'getLanguages')
      .mockImplementation(
        jest.fn(() => Promise.resolve(fakeFetchData)) as jest.Mock,
      );
  });

  test('Should return initial state', () => {
    const expectedState: LanguageState = {
      activeLang: 'en',
      languages: [],
      loading: true,
    };
    expect(LanguageReducer(undefined, { type: undefined })).toEqual(
      expectedState,
    );
  });

  test('Should set activeLanguage in state', () => {
    const prevState: LanguageState = {
      activeLang: 'en',
      languages: [],
      loading: true,
    };
    const expectedState: LanguageState = {
      activeLang: 'ee',
      languages: [],
      loading: true,
    };
    expect(LanguageReducer(prevState, setActiveLanguage('ee'))).toEqual(
      expectedState,
    );
  });

  test('Should fetch languages and expect to be same as fakeFetchData and set loading to false', async () => {
    const expectedState: LanguageState = {
      activeLang: 'en',
      languages: fakeFetchData,
      loading: false,
    };

    await testStore.dispatch(fetchLanguages());
    expect(testStore.getState()).toEqual(expectedState);
  });
});
