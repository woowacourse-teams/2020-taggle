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

      <v-list dense nav>
        <v-list-item v-for="item in items" :key="item.title" link @click="toggleBookmarkList">
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <TagList v-show="isBookmarkList" />
  </div>
</template>

<script>
import TagList from './components/TagList'

export default {
  name: 'BookmarkHeader',
  components: {
    TagList
  },
  data() {
    return {
      items: [{ title: 'tags', icon: 'local_offer' }],
      isBookmarkList: false
    }
  },
  methods: {
    toggleBookmarkList: function() {
      this.isBookmarkList = !this.isBookmarkList
    }
  }
}
</script>
