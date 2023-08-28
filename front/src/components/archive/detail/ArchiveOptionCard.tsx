import { Flex, Tag, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { archiveOptionDetailInterface } from '@pages/Archive/detail';
import { css } from '@emotion/react';

export const OptionCard = ({
  info,
}: {
  info: archiveOptionDetailInterface;
}) => {
  return (
    <OptionDetailCard>
      <OptionImg src={info.photoUrl} alt="" />
      <Text typo="Heading3_Medium">{info.optionName}</Text>
      {info.childOptionNames.length !== 0 && (
        <Text typo="Body2_Medium" palette="Primary">
          {info.childOptionNames.join(' | ')}
        </Text>
      )}

      {info.tags.length !== 0 && (
        <Flex
          align="flex-start"
          justify="flex-start"
          gap={8}
          height="auto"
          css={css`
            flex-wrap: wrap;
          `}
        >
          {info.tags.map((it, idx) => (
            <Tag key={`infoTage_${idx}`} desc={it.tagContent} />
          ))}
        </Flex>
      )}
    </OptionDetailCard>
  );
};

const OptionDetailCard = styled(Flex)`
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  width: 335px;
  height: auto;
  padding: 15px;
  gap: 12px;

  border: 2px solid ${theme.palette.Sand};
  border-radius: 8px;
`;

const OptionImg = styled.img`
  width: 305px;
  border-radius: 8px;
  object-fit: cover;
`;
