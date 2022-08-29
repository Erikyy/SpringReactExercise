import CurrencySelector from '@app/features/Currency/CurrencySelector';
import UtilsService from '@app/service/UtilsService';
import { act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {
  mockAppSelector,
  mockAppDispatch,
  renderWithProviders,
  mockStore,
} from '../../../jest/CompontentTestingUtils';
import * as RootStore from '@app/store/RootStore';
import { fetchCurrencies } from '@app/features/Currency/CurrencySlice';

const fakeFetchData = ['eur', 'usd'];

describe('<LanguageSelector />', () => {
  beforeAll(() => {
    jest
      .spyOn(UtilsService, 'getCurrencies')
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
    const component = renderWithProviders(<CurrencySelector />, mockStore);

    await act(async () => {
      await mockStore.dispatch(fetchCurrencies());
    });

    expect(mockStore.getState().currencies.currencies).not.toHaveLength(0);

    const selectInput = component.ui.queryByTestId('currency-selector')
      ?.childNodes[0] as HTMLSelectElement;

    expect(selectInput).not.toBe(null);
    expect(selectInput).toHaveProperty('value', 'eur');

    await act(async () => {
      await userEvent.selectOptions(selectInput, 'usd');
    });

    expect(selectInput).toHaveProperty('value', 'usd');

    expect(mockStore.getState().currencies.activeCurrency).toEqual('usd');
  });
});
