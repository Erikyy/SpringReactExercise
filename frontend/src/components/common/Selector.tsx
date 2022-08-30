import React, { useState } from 'react';
import type { FC } from 'react';
import { FormControl, InputLabel, Select } from '@mui/material';
import { useTranslation } from 'react-i18next';

interface SelectorProps {
  id: string;
  label: string;
  data: string[];
  value: string;
  testId?: string;
  onChange: (val: string) => void;
}

const Selector: FC<SelectorProps> = ({
  id,
  label,
  data,
  value,
  onChange,
  testId,
}) => {
  const [open, setOpen] = useState(false);

  const { t } = useTranslation();

  return (
    <FormControl sx={{ minWidth: 80 }} size='small' variant='outlined'>
      <InputLabel id={`${id}-label`}>{t(label)}</InputLabel>
      <Select
        native={true}
        data-testid={testId}
        open={open}
        onClose={() => setOpen(false)}
        onOpen={() => setOpen(true)}
        onChange={(e) => {
          onChange(e.target.value);
        }}
        labelId={`${id}-label`}
        id={`${id}`}
        value={value}
        label={t(label)}
      >
        {data.map((item, i) => {
          return (
            <option key={`${id}-${item}-####`} value={item}>
              {item}
            </option>
          );
        })}
      </Select>
    </FormControl>
  );
};

export default Selector;
