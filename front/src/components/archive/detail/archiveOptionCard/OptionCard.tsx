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
      <OptionTop direction="column" align="flex-start">
        <OptionImg
          src={info.photoUrl}
          alt=""
          css={css`
            object-fit: cover;
          `}
        />
        <Text typo="Heading3_Medium" margin="20px 0 9px 0">
          {info.optionName}
        </Text>
      </OptionTop>
      <Flex justify="flex-start" height="auto">
        <Text typo="Body2_Medium" palette="Primary" margin="0 0 10px 0">
          {info.childOptionNames.join(' | ')}
        </Text>
      </Flex>

      <Flex
        gap={8}
        justify="flex-start"
        css={css`
          flex-wrap: wrap;
        `}
      >
        {info.tags.map((it, idx) => (
          <Tag key={`infoTage_${idx}`} desc={it.tagContent} />
        ))}
      </Flex>
    </OptionDetailCard>
  );
};

const OptionTop = styled(Flex)`
  width: 314px;
  flex-direction: column;
  align-items: flex-start;

  border-bottom: 1px solid ${theme.palette.LightGray};

  margin-bottom: 12px;
`;

const OptionDetailCard = styled(Flex)`
  width: 335px;
  height: auto;
  padding: 15px 15px 30px 15px;
  flex-direction: column;
  border: 2px solid ${theme.palette.Sand};

  border-radius: 8px;
`;

const OptionImg = styled.img`
  width: 314px;
  height: 130px;
  border-radius: 8px;
  object-fit: cover;
`;
