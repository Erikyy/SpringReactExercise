import React, { useEffect } from 'react';
import type { FC } from 'react';
import Selector from '@app/components/common/Selector';
import { useAppDispatch, useAppSelector } from '@app/store/RootStore';
import { fetchCurrencies, setActiveCurrency } from './CurrencySlice';
import { useTranslation } from 'react-i18next';
import { T_CURRENCY } from '@app/constants';

const CurrencySelector: FC = () => {
  const dispatch = useAppDispatch();

  const { activeCurrency, currencies, loading } = useAppSelector(
    (state) => state.currencies,
  );

  if (loading) {
    return null;
  }
  return (
    <Selector
      testId='currency-selector'
      id='currency-selector'
      data={currencies}
      label={T_CURRENCY}
      value={activeCurrency}
      onChange={(val) => {
        dispatch(setActiveCurrency(val));
      }}
    />
  );
};

export default CurrencySelector;
