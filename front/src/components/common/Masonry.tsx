import { RefObject } from 'react';

export function masonryLayout({
  element,
}: {
  element: RefObject<HTMLElement> | null;
}) {
  if (!element || !element.current) {
    return;
  }

  const masonryContainer = element.current;
  const itemStyle = getComputedStyle(masonryContainer);
  const gap = parseInt(itemStyle.gap);
  const gridAutoRow = parseInt(itemStyle.gridAutoRows);

  Array.from(masonryContainer.children).forEach((item) => {
    const element = item as HTMLElement;
    const innerElementHeight = element.children[0].scrollHeight;

    const requiredRows = Math.ceil(innerElementHeight / (gap + gridAutoRow));
    element.style.gridRowEnd = `span ${requiredRows}`;
  });
}
