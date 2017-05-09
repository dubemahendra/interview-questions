from pprint import pprint

class DirectedGraph(object):
  def __init__(self, adjacency_list):

    # Map from node to list of neighboring nodes
    self._adjacency_list = adjacency_list

  def _get_neighbors(self, node):
    """Returns the list of neighbors for a node. Throws if the node is not in the graph."""

    return self._adjacency_list[node]

  def print_graph(self):
    """Prints the adjacency list prettily."""

    pprint(self._adjacency_list)

  def has_cycle(self, starting_node):
    """Returns whether or not the graph connected to starting_node has a cycle or not."""

    raise NotImplementedError

  def bfs(self, starting_node):
    """Returns a list of all nodes connected to starting_node in bfs order."""

    raise NotImplementedError

  def dfs(self, starting_node):
    """Returns a list of all nodes connected to starting_node in dfs order."""

    raise NotImplementedError


if __name__ == "__main__":
  graph_a = DirectedGraph({'A': ['B', 'C'],
                           'B': ['C'],
                           'C': ['D'],
                           'D': []
                           })


  graph_b = DirectedGraph({'A': ['B', 'C'],
                           'B': ['D', 'E', 'A'],
                           'C': ['E', 'F'],
                           'D': [],
                           'E': [],
                           'F': ['G'],
                           'G': []
                           })

  graph_c = DirectedGraph({'A': ['B', 'C'],
                           'B': ['D', 'E'],
                           'C': ['F', 'G'],
                           'D': [],
                           'E': [],
                           'F': [],
                           'G': ['A']
                           })

  nodes = ['A', 'B', 'C', 'D', 'E', 'F']
  import random
  random.seed(10)
  random_graph = DirectedGraph({start: random.sample(nodes, random.randint(0, 3)) for start in nodes})

  random_graph.print_graph()
  print(random_graph.has_cycle('A'))

  assert(graph_a.has_cycle('A') is False)
  assert(graph_b.has_cycle('A') is True)
  assert(graph_c.has_cycle('A') is True)


  assert(graph_a.bfs('A') == ['A', 'B', 'C', 'D'])
  assert(graph_a.dfs('A') == ['A', 'B', 'C', 'D'])
  assert(graph_a.bfs('D') == ['D'])
  assert(graph_a.bfs('B') == ['B', 'C', 'D'])

  assert(graph_b.bfs('A') == ['A', 'B', 'C', 'D', 'E', 'F', 'G'])
  assert(graph_b.dfs('A') == ['A', 'B', 'D', 'E', 'C', 'F', 'G'])
  assert(graph_b.dfs('B') == ['B', 'D', 'E', 'A', 'C', 'F', 'G'])
  assert(graph_b.dfs('B') == ['B', 'D', 'E', 'A', ])


