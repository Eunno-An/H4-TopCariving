import styled from '@emotion/styled';
import { Flex } from './Flex';
import { KeyOfPalette } from '@styles/theme';

interface ButtonProps {
  width: number;
  type: 'medium' | 'small';
  text: string;
  backgroundColor: KeyOfPalette;
}

export const Button = ({ width, type, text, backgroundColor }: ButtonProps) => {
  const color: KeyOfPalette =
    backgroundColor === 'Primary'
      ? 'White'
      : backgroundColor === 'White'
      ? 'Primary'
      : backgroundColor === 'LightGray'
      ? 'DarkGray'
      : 'Primary';
  return (
    <ButtonContainer
      width={width}
      backgroundColor={backgroundColor}
      color={color}
      height={type === 'medium' ? 56 : 50}
      borderRadius="8px"
      justify="center"
      align="center"
    >
      {text}
    </ButtonContainer>
  );
};

const ButtonContainer = styled(Flex)<{ color: string }>`
  color: ${(props) => props.color};
  white-space: nowrap;
  cursor: pointer;
`;
