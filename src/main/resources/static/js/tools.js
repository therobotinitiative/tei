/**
 * Sort elements by index in ascending order. Works only for elements that have property [index].
 */
function orderByIndex(a, b)
{
	if (a.index > b.index)
	{
		return 1;
	}
	if (a.index < b.index)
	{
		return -1;
	}
	return 0;
}