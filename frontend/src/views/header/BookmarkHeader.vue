<template>
  <div>
    <v-navigation-drawer v-model="drawer" :mini-variant.sync="mini" permanent>
      <v-list-item class="px-2">
        <v-list-item-avatar>
          <v-img src="@/assets/images/TaggleLog.png"></v-img>
        </v-list-item-avatar>
        <v-btn icon @click.stop="mini = !mini">
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
      </v-list-item>
      <v-divider></v-divider>
      <v-list dense>
        <v-list-item v-for="item in items" :key="item.title" link>
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <TagList />
  </div>
</template>
<script>
import TagList from './components/BookmarkList'

export default {
  components: {
    TagList
  },

  data() {
    return {
      drawer: true,
      items: [
        {
          title: '',
          icon: 'local_offer'
        }
      ],
      mini: true
    }
  }
}
</script>
<style></style>
