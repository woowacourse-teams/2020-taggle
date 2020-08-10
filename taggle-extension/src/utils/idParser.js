const URI_SEPARATOR = '/';

export default (resourceUrl) => {
  const locations = resourceUrl.split(URI_SEPARATOR);
  const resourceId = locations[locations.length - 1];
  return resourceId;
};
