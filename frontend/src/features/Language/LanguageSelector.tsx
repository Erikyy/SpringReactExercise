import React from 'react';
import type { FC } from 'react';
import { FormControl, InputLabel } from '@mui/material';
import Selector from '@app/common/Selector';
import { useAppDispatch, useAppSelector } from '@app/store/RootStore';
import { setActiveLanguage } from './LanguageSlice';
import { useTranslation } from 'react-i18next';

const LanguageSelector: FC = () => {
  const { t, i18n } = useTranslation();
  const dispatch = useAppDispatch();
  const { activeLang, languages, loading } = useAppSelector(
    (state) => state.languages,
  );

  if (loading) {
    return null;
  }
  return (
    <Selector
      id='language-selector'
      data={languages}
      defaultValue='en'
      label='Language'
      value={activeLang}
      onChange={(val) => {
        dispatch(setActiveLanguage(val));
        i18n.changeLanguage(val);
      }}
    />
  );
};

export default LanguageSelector;
