import { ButtonHTMLAttributes } from 'react';
import { Flex } from './Flex';
import styled from '@emotion/styled';

export const Button = ({
  children,
}: ButtonHTMLAttributes<HTMLButtonElement>) => {
  return (
    <ButtonContainer
      width="auto"
      padding="13px 48px"
      borderRadius="8px"
      backgroundColor="Primary"
    >
      {children}
    </ButtonContainer>
  );
};

const ButtonContainer = styled(Flex)`
  color: white;
  white-space: nowrap;
  cursor: pointer;
`;
