import styled from '@emotion/styled';
import { Flex } from '..';
import { theme } from '@styles/theme';

export const OptionLoader = () => {
  return (
    <OptionLoaderContainer direction="column" gap={10} borderRadius="8px">
      <TagOptionLoader height={52} borderRadius="8px"></TagOptionLoader>
      <TagOptionLoader height={152} borderRadius="8px"></TagOptionLoader>
    </OptionLoaderContainer>
  );
};

const OptionLoaderContainer = styled(Flex)``;

const TagOptionLoader = styled(Flex)`
  background-color: ${theme.palette.LightGray};

  animation: colorLoader 1s ease-in-out infinite;
  @keyframes colorLoader {
    0% {
      background-color: rgba(228, 220, 211, 0.2);
    }

    50% {
      background-color: rgba(228, 220, 211, 0.4);
    }

    100% {
      background-color: rgba(228, 220, 211, 0.2);
    }
  }
`;
