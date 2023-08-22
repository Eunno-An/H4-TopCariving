import styled from '@emotion/styled';
import { Flex, Text } from '.';
import { theme } from '@styles/theme';
import { useToast } from '@contexts/ToastContext';

const Toast = () => {
  const { isOpen, content } = useToast();
  return (
    <Container isOpen={isOpen}>
      <Text palette="White" typo="Body2_Regular">
        {content}
      </Text>
    </Container>
  );
};

export default Toast;

const Container = styled(Flex)<{ isOpen: boolean }>`
  position: fixed;
  top: 10px;
  right: ${({ isOpen }) => (isOpen ? '10px' : '-1000px')};

  height: 40px;
  width: auto;
  padding: 30px;
  border-radius: 10px;

  background-color: ${theme.palette.Primary};
  opacity: 0.9;
  transition: ease-out 0.5s;
  z-index: 2;
`;
