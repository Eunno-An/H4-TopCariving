import { ButtonHTMLAttributes } from 'react';
import styled from '@emotion/styled';
import { Flex } from './Flex';

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
