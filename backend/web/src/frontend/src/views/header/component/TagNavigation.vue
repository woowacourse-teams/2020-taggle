<template>
  <v-navigation-drawer permanent>
    <v-text-field
      prepend-inner-icon="mdi-magnify"
      placeholder="Tag Search"
      filled
      rounded
      dense
      class="ma-2"
      hide-details="true"
    ></v-text-field>
    <TagNavigationBody />
    <template v-slot:append>
      <TagNavigationFooter />
    </template>
  </v-navigation-drawer>
</template>

<script>
import TagNavigationFooter from '@/views/header/component/TagNavigationFooter.vue';
import TagNavigationBody from '@/views/header/component/TagNavigationBody.vue';

export default {
  name: 'TagNavigation',
  components: {
    TagNavigationBody,
    TagNavigationFooter,
  },
};
</script>
