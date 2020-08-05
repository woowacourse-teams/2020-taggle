<template>
  <div>
    <v-navigation-drawer permanent app mini-variant>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="title">
            <v-img src="@/assets/images/TaggleLog.png"></v-img>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense nav class="d-flex flex-wrap" height="94%">
        <v-list-item v-for="(item, index) in items" :key="index" link :class="`order-${index}`" style="height:0; py-0">
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item>
        <v-list-item class="pa-0 order-last align-self-end" style="height:0;" link>
          <v-avatar min-width="0" height="40px" width="40px">
            <img src="https://cdn.vuetifyjs.com/images/john.jpg" alt="John" />
          </v-avatar>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <TagList />
  </div>
</template>

<script>
import TagList from '@/views/header/components/TagList.vue';

export default {
  name: 'BookmarkHeader',
  components: {
    TagList,
  },
  data() {
    return {
      items: [{ icon: 'local_offer' }],
    };
  },
};
</script>

<style></style>
