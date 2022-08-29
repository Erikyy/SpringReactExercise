import LanguageSelector from '@app/features/Language/LanguageSelector';
import { fetchLanguages } from '@app/features/Language/LanguageSlice';
import UtilsService from '@app/service/UtilsService';
import { act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {
  renderWithProviders,
  mockAppSelector,
  mockStore,
  mockAppDispatch,
} from '../../../jest/CompontentTestingUtils';
import * as RootStore from '@app/store/RootStore';

const fakeFetchData = ['en', 'et'];

describe('<LanguageSelector />', () => {
  beforeAll(() => {
    jest
      .spyOn(UtilsService, 'getLanguages')
      .mockImplementation(
        jest.fn(() => Promise.resolve(fakeFetchData)) as jest.Mock,
      );

    jest.mock('react-i18next', () => ({
      useTranslation: () => {
        return {
          t: (str: string) => str,
          i18n: {
            changeLanguage: () => new Promise(() => {}),
          },
        };
      },
    }));

    jest
      .spyOn(RootStore, 'useAppSelector')
      .mockImplementation((state) => mockAppSelector(state));

    jest
      .spyOn(RootStore, 'useAppDispatch')
      .mockImplementation(jest.fn(() => mockAppDispatch()));
  });

  test('Should render component and be updated with data', async () => {
    const component = renderWithProviders(<LanguageSelector />, mockStore);

    await act(async () => {
      await mockStore.dispatch(fetchLanguages());
    });

    expect(mockStore.getState().languages.languages).not.toHaveLength(0);

    const selectInput = component.ui.queryByTestId('language-selector')
      ?.childNodes[0] as HTMLSelectElement;

    expect(selectInput).not.toBe(null);
    expect(selectInput).toHaveProperty('value', 'en');

    await act(async () => {
      await userEvent.selectOptions(selectInput, 'et');
    });

    expect(selectInput).toHaveProperty('value', 'et');

    expect(mockStore.getState().languages.activeLang).toEqual('et');
  });
});
