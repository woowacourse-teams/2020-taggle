<template>
  <v-card class="header-container" height="100%" width="306">
    <v-navigation-drawer permanent width="100%">
      <v-row class="fill-height" no-gutters>
        <v-navigation-drawer dark mini-variant mini-variant-width="56" permanent>
          <v-list-item class="px-2">
            <v-list-item-avatar>
              <v-img src="@/assets/images/TaggleLog.png"></v-img>
            </v-list-item-avatar>
          </v-list-item>
          <v-divider></v-divider>
          <v-list dense nav>
            <v-list-item :key="title" link v-for="{ icon, title } in icons">
              <v-list-item-action>
                <v-icon>{{ icon }}</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>{{ title }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>
        <v-card tile width="250">
          <v-list v-for="{ id, title, tags } in categories" :key="id" class="grow">
            <v-list-group class="text-left mt-2" link value="true">
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title class="font-weight-black text-h7">{{ title }}</v-list-item-title>
                </v-list-item-content>
              </template>
              <v-list-item v-for="{ id, name } in tags" :key="id" @click.prevent="fetchBookmarks">
                <v-list-item-content>
                  {{ name }}
                </v-list-item-content>
              </v-list-item>
            </v-list-group>
          </v-list>
        </v-card>
      </v-row>
    </v-navigation-drawer>
  </v-card>
</template>

<script>
import { categories } from '@/utils/mockTags.js';

export default {
  name: 'BookmarkHeader',
  methods: {
    fetchBookmarks() {
      if (this.$router.currentRoute.path !== '/bookmark') {
        this.$router.replace('/bookmark');
      }
    },
  },
  data() {
    return {
      icons: [{ icon: 'local_offer', title: 'tag' }],
      categories,
    };
  },
};
</script>

<style>
.header-container {
  position: fixed !important;
}
</style>
