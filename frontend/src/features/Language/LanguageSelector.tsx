import React from 'react';
import type { FC } from 'react';
import Selector from '@app/components/common/Selector';
import { useAppDispatch, useAppSelector } from '@app/store/RootStore';
import { setActiveLanguage } from './LanguageSlice';
import { useTranslation } from 'react-i18next';
import { T_LANGUAGE } from '@app/constants';

const LanguageSelector: FC = () => {
  const { i18n } = useTranslation();
  const dispatch = useAppDispatch();
  const { activeLang, languages, loading } = useAppSelector(
    (state) => state.languages,
  );

  if (loading || !languages) {
    return null;
  }
  return (
    <Selector
      testId='language-selector'
      id='language-selector'
      data={languages}
      label={T_LANGUAGE}
      value={activeLang}
      onChange={(val) => {
        dispatch(setActiveLanguage(val));
        i18n.changeLanguage(val);
      }}
    />
  );
};

export default LanguageSelector;
