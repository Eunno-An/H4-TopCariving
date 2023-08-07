import styled from '@emotion/styled';
import { Flex } from './Flex';
import { KeyOfPalette, theme } from '@styles/theme';

interface ButtonProps {
  width: number;
  type: 'large' | 'medium' | 'small';
  isIcon?: boolean;
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
      height={type === 'large' ? 56 : type === 'medium' ? 50 : 30}
      borderRadius="8px"
      justify="center"
      align="center"
    >
      {text}
    </ButtonContainer>
  );
};

const ButtonContainer = styled(Flex)<{ color: KeyOfPalette }>`
  color: ${(props) => theme.palette[props.color]};
  white-space: nowrap;
  cursor: pointer;
`;
